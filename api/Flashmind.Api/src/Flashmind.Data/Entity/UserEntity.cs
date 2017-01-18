using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Entity
{
    public class UserEntity : BaseEntity
    {

        public string Username { get; set; }

        public string Password { get; set; }

        public string Name { get; set; }

        public string ExternalId { get; set; }

        public bool IsRegistred { get; set; }

        public List<DeckEntity> Decks { get; set; }

        public List<JoinedDeckEntity> JoinedDecks { get; set; }

        public string Token { get; set; }

    }
}