using System;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Entity
{
    public class DeckCardRoundEntity : BaseEntity
    {

        public bool IsCorrect { get; set; }

        public Guid CardId { get; set; }

        [ForeignKey("CardId")]
        public CardEntity Card { get; set; }


        public Guid DeckRoundId { get; set; }

        [ForeignKey("DeckRoundId")]
        public DeckRoundEntity Round { get; set; }

//        public const string TableName = "DeckCardRounds";
//
//        public static void Setup(ModelBuilder builder)
//        {
//            builder.Entity<DeckCardRoundEntity>()
//                .ToTable(TableName)
//                .HasKey(h => h.Id);
//
//            builder.Entity<DeckCardRoundEntity>()
//                .HasOne(u => u.Card)
//                .WithMany(b => b.DeckCardRounds)
//                .HasForeignKey(p => p.CardForeignKey);
//
//            builder.Entity<DeckCardRoundEntity>()
//                .HasOne(u => u.Round)
//                .WithMany(b => b.DeckCardRounds)
//                .HasForeignKey(p => p.DeckRoundForeignKey);
//        }

    }
}