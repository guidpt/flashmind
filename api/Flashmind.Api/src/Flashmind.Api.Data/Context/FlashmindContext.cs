using Microsoft.EntityFrameworkCore;

namespace Flashmind.Api.Data.Context
{
    public class FlashmindContext : DbContext
    {
        public FlashmindContext(DbContextOptions<FlashmindContext> options)
            : base(options)
        {
            
        }
    }
}