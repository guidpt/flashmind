using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Security.Principal;
using System.Text;
using System.Threading.Tasks;
using Flashmind.Api.Application.Repository;
using Flashmind.Api.Token;
using Flashmind.Api.Token.Middleware;
using Flashmind.Api.Token.Repository;
using Flashmind.Data.Context;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.Logging;
using Microsoft.IdentityModel.Tokens;

namespace Flashmind.Api.Authenticate
{
    public class Startup
    {
        private static readonly string secretKey = "WAApmxLodsatCHuyH4Anrkrc";

        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true);

            if (env.IsEnvironment("Development"))
            {
                // This will push telemetry data through Application Insights pipeline faster, allowing you to view results immediately.
                builder.AddApplicationInsightsSettings(developerMode: true);
            }

            builder.AddEnvironmentVariables();
            Configuration = builder.Build();
        }

        public IConfigurationRoot Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container
        public void ConfigureServices(IServiceCollection services)
        {
            // Add framework services.
            services.AddApplicationInsightsTelemetry(Configuration);

            services.AddSingleton<IConfiguration>(Configuration);
            services.AddDbContext<FlashmindContext>((options =>
                options.UseSqlServer(Configuration["ConnectionStrings:DefaultConnection"])));
            services.TryAddSingleton<UserRepository>();

            services.AddMvc();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory)
        {
            loggerFactory.AddConsole(Configuration.GetSection("Logging"));
            loggerFactory.AddDebug();

            app.UseApplicationInsightsRequestTelemetry();

            app.UseApplicationInsightsExceptionTelemetry();

            var signingKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(secretKey));
            var jwtAppSettingOptions = Configuration.GetSection(nameof(TokenProviderOptions));

            app.UseSimpleTokenProvider(new TokenProviderOptions
            {
                Path = "/api/token",
                Audience = jwtAppSettingOptions[nameof(TokenProviderOptions.Audience)],
                Issuer = jwtAppSettingOptions[nameof(TokenProviderOptions.Issuer)],
                SigningCredentials = new SigningCredentials(signingKey, SecurityAlgorithms.HmacSha256),
                IdentityResolver = GetIdentity,
                TokenResolver = SaveToken,
                Expiration = TimeSpan.FromDays(1)
            });

            StartupAuth.ConfigureAuth(app, secretKey, signingKey, jwtAppSettingOptions);

            app.UseMvc();
        }

        private async Task<ClaimsIdentity> GetIdentity(string username, string password, UserRepository repository)
        {
            var user = await repository.FirstOrDefaultAsync(f => f.Username == username);

            if (user == null)
                return null;

            if (!user.IsRegistred || AuthenticationHelper.VerifyPasswordHash(password, user.Password))
                return new ClaimsIdentity(new GenericIdentity(username, "Token"), new Claim[] {});

            return null;
        }

        private async Task SaveToken(string accessToken, string username, UserRepository repository)
        {
            var user = await repository.FirstOrDefaultAsync(f => f.Username == username);


            if (user.Token == accessToken)
                return;

            user.Token = accessToken;

            await repository.UpdateAndSaveAsync(user);
        }
    }
}