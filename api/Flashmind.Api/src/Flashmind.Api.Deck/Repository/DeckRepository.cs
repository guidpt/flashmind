using Flashmind.Api.Application.Repository;
using Flashmind.Data.Context;
using Flashmind.Data.Entity;

namespace Flashmind.Api.Deck.Repository
{

    public class DeckRepository : EfRepository<DeckEntity>
    {
        public DeckRepository(FlashmindContext dataContext) : base(dataContext)
        {

        }
    }
}