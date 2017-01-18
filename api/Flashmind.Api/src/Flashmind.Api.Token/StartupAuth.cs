using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Security.Principal;
using System.Text;
using System.Threading.Tasks;
using Flashmind.Api.Token.Middleware;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;

namespace Flashmind.Api.Token
{
    public class StartupAuth
    {
        public static void ConfigureAuth(IApplicationBuilder app, string secretKey, SymmetricSecurityKey signingKey, IConfigurationSection jwtAppSettingOptions)
        {
            var tokenValidationParameters = new TokenValidationParameters
            {
                // The signing key must match!
                ValidateIssuerSigningKey = true,
                IssuerSigningKey = signingKey,
                // Validate the JWT Issuer (iss) claim
                ValidateIssuer = true,
                ValidIssuer = jwtAppSettingOptions[nameof(TokenProviderOptions.Issuer)],
                // Validate the JWT Audience (aud) claim
                ValidateAudience = true,
                ValidAudience = jwtAppSettingOptions[nameof(TokenProviderOptions.Audience)],
                // Validate the token expiry
                ValidateLifetime = true,
                // If you want to allow a certain amount of clock drift, set that here:
                ClockSkew = TimeSpan.Zero
            };

            app.UseJwtBearerAuthentication(new JwtBearerOptions
            {
                AutomaticAuthenticate = true,
                AutomaticChallenge = true,
                TokenValidationParameters = tokenValidationParameters
            });
        }


    }
}