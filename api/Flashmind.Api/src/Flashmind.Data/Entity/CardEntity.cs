using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Entity
{
    public class CardEntity : BaseEntity
    {

        public string QuestionString { get; set; }

        public string QuestionImageUrl { get; set; }

        public string AnswerString { get; set; }

        public string AnswerImageUrl { get; set; }


        public Guid DeckForeignKey { get; set; }

        //Virtual

        [ForeignKey("DeckForeignKey")]
        public DeckEntity Deck { get; set; }


//        public const string TableName = "Cards";
//
//        public static void Setup(ModelBuilder builder)
//        {
//            builder.Entity<CardEntity>()
//                .ToTable(TableName)
//                .HasKey(h => h.Id);
//
//            builder.Entity<CardEntity>()
//                .HasOne(u => u.Deck)
//                .WithMany(b => b.Cards)
//                .HasForeignKey(p => p.DeckForeignKey);
//        }



    }
}