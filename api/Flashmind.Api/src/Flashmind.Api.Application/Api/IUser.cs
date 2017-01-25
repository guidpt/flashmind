using System.Collections.Generic;
using System.Threading.Tasks;
using Flashmind.Api.Application.Proxy;
using RestEase;

namespace Flashmind.Api.Application.Api
{
    public interface IUser
    {
        [Get("api/user/me")]
        Task<UserProxy> GetMeAsync([Header("Authorization")] string authorization);

        [Get("api/user/{id}")]
        Task<UserProxy> GetUserAsync([Header("Authorization")] string authorization, [Path] string id);

    }
}