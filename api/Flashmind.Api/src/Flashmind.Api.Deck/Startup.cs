using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Flashmind.Api.Application.Api;
using Flashmind.Api.Deck.Repository;
using Flashmind.Api.Token;
using Flashmind.Api.Token.Middleware;
using Flashmind.Api.Token.Repository;
using Flashmind.Data.Context;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc.Authorization;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.Logging;
using Microsoft.IdentityModel.Tokens;
using RestEase;

namespace Flashmind.Api.Deck
{
    public class Startup
    {
        private static readonly string secretKey = "WAApmxLodsatCHuyH4Anrkrc";

        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true)
                .AddEnvironmentVariables();
            Configuration = builder.Build();
        }

        public static string Token { get; private set; }
        
        public IConfigurationRoot Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            Login().Wait();
            // Add framework services.
            services.AddMvc(config =>
            {
                var policy = new AuthorizationPolicyBuilder().RequireAuthenticatedUser().Build();
                config.Filters.Add(new AuthorizeFilter(policy));
            });

            services.AddSingleton<IConfiguration>(Configuration);

            services.AddSingleton(RestClient.For<IUser>(Configuration["Login:Url"]));
            services.AddDbContext<FlashmindContext>((options =>
                options.UseSqlServer(Configuration["ConnectionStrings:DefaultConnection"])));
            services.TryAddSingleton<DeckRepository>();
        }

        public async Task Login()
        {
            var login = RestClient.For<ILogin>(Configuration["Login:Url"]);

            var token = await login.Login(new Dictionary<string, object>
            {
                {"grant_type", "password"},
                {"username", Configuration["Login:Username"]},
                {"password", Configuration["Login:Password"]},
                {"app_type", 0}
            });
            Token = $"Bearer {token.AccessToken}";
        }

        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory)
        {
            loggerFactory.AddConsole(Configuration.GetSection("Logging"));
            loggerFactory.AddDebug();

            var signingKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(secretKey));
            var jwtAppSettingOptions = Configuration.GetSection(nameof(TokenProviderOptions));
            StartupAuth.ConfigureAuth(app, secretKey, signingKey, jwtAppSettingOptions);

            app.UseMvc();
        }
    }
}
