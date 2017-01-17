using System;

namespace Flashmind.Api.Data.Entity
{
    public class JoinedDeckEntity : BaseEntity
    {

        public Guid UserForeignKey { get; set; }

        //Virtual
        public UserEntity User { get; set; }



        public Guid DeckForeignKey { get; set; }

        //Virtual
        public DeckEntity Deck { get; set; }
    }
}