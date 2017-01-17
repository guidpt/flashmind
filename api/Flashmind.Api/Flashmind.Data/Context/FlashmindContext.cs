using Flashmind.Data.Entity;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Context
{
    public class FlashmindContext : DbContext
    {
        public FlashmindContext(DbContextOptions<FlashmindContext> options)
            : base(options)
        {
            
        }

        public DbSet<UserEntity> Users { get; set; }
        public DbSet<DeckEntity> Decks { get; set; }
        public DbSet<JoinedDeckEntity> JoinedDecks { get; set; }
        public DbSet<CardEntity> Cards { get; set; }
        public DbSet<DeckRoundEntity> DeckRounds { get; set; }
        public DbSet<DeckCardRoundEntity> DeckCardRounds { get; set; }

//        protected override void OnModelCreating(ModelBuilder builder)
//        {
//            base.OnModelCreating(builder);
//        }

    }
}