using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Entity
{
    public class DeckRoundEntity : BaseEntity
    {
        public double Score { get; set; }

        public Guid DeckForeignKey { get; set; }

        [ForeignKey("DeckForeignKey")]
        public DeckEntity Deck { get; set; }

        public List<DeckCardRoundEntity> DeckCardRounds { get; set; }


//        public const string TableName = "DeckRounds";
//
//        public static void Setup(ModelBuilder builder)
//        {
//            builder.Entity<DeckRoundEntity>()
//                .ToTable(TableName)
//                .HasKey(h => h.Id);
//
//            builder.Entity<DeckRoundEntity>()
//                .HasOne(u => u.Deck)
//                .WithMany(b => b.Rounds)
//                .HasForeignKey(p => p.DeckForeignKey);
//        }
    }
}