using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Api.Data.Entity
{
    public class UserEntity : BaseEntity
    {

        public string Username { get; set; }

        public string ExternalId { get; set; }

        public bool IsRegistred { get; set; }

        public List<DeckEntity> Decks { get; set; }

        public List<JoinedDeckEntity> JoinedDecks { get; set; }


        public const string TableName = "Users";

        public static void Setup(ModelBuilder builder)
        {
            builder.Entity<UserEntity>().ToTable(TableName);

            builder.Entity<UserEntity>()
                .HasKey(x => x.Id);

        }
    }
}