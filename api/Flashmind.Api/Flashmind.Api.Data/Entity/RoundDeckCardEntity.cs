using System;

namespace Flashmind.Api.Data.Entity
{
    public class RoundDeckCardEntity : BaseEntity
    {

        public bool IsCorrect { get; set; }

        public Guid CardForeignKey { get; set; }

        public Guid RoundDeckForeignKey { get; set; }

    }
}