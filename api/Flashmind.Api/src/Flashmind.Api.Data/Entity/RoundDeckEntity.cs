using System;

namespace Flashmind.Api.Data.Entity
{
    public class RoundDeckEntity : BaseEntity
    {
        public double Score { get; set; }

        public Guid DeckForeignKey { get; set; }

    }
}