using Flashmind.Api.Application.Repository;
using Flashmind.Data.Context;
using Flashmind.Data.Entity;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Api.Token.Repository
{
    public class UserRepository : EfRepository<UserEntity>
    {
        public UserRepository(FlashmindContext dataContext) : base(dataContext)
        {

        }
    }
}