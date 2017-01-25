using System.Collections.Generic;
using System.Threading.Tasks;
using Flashmind.Api.Application.Proxy;
using RestEase;

namespace Flashmind.Api.Application.Api
{
    [Header("Content-Type", "application/x-www-form-urlencoded")]
    public interface ILogin
    {
        [Post("api/token")]
        Task<TokenProxy> Login([Body(BodySerializationMethod.UrlEncoded)] Dictionary<string, object> data);

        [Post("api/token")]
        [AllowAnyStatusCode]
        Task<Response<TokenProxy>> LoginResponse([Body(BodySerializationMethod.UrlEncoded)] Dictionary<string, object> data);
    }
}