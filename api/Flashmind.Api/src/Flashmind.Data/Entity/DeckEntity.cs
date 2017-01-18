using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Entity
{
    public class DeckEntity : BaseEntity
    {
        public string Tags { get; set; }

        public PrivacyEnum Privacy { get; set; }

        public string Color { get; set; }

        public Guid UserForeignKey { get; set; }

        //Virtual
        [ForeignKey("UserForeignKey")]
        public UserEntity User { get; set; }

        public List<CardEntity> Cards { get; set; }


//        public const string TableName = "Decks";
//
//        public static void Setup(ModelBuilder builder)
//        {
//            builder.Entity<DeckEntity>()
//                .ToTable(TableName)
//                .HasKey(h => h.Id);
//
//            builder.Entity<DeckEntity>()
//                .HasOne(u => u.User)
//                .WithMany(b => b.Decks)
//                .HasForeignKey(p => p.UserForeignKey);
//        }
    }

    public enum PrivacyEnum
    {
        Private,
        Public
    }
}