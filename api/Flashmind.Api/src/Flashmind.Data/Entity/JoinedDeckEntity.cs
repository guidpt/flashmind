using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Entity
{
    public class JoinedDeckEntity : BaseEntity
    {

        [Required]
        public Guid UserId { get; set; }

        //Virtual
        [ForeignKey("UserId")]
        public UserEntity User { get; set; }

        [Required]
        public Guid DeckId { get; set; }

        //Virtual
        [ForeignKey("DeckId")]
        public DeckEntity Deck { get; set; }



//        public const string TableName = "JoinedDeck";
//
//        public static void Setup(ModelBuilder builder)
//        {
//            builder.Entity<JoinedDeckEntity>()
//                .ToTable(TableName)
//                .HasKey(h => h.Id);
//
//            builder.Entity<JoinedDeckEntity>()
//                .HasOne(u => u.User)
//                .WithMany(b => b.JoinedDecks)
//                .HasForeignKey(p => p.UserForeignKey);
//
//            builder.Entity<JoinedDeckEntity>()
//                .HasOne(u => u.Deck)
//                .WithMany(b => b.JoinedDecks)
//                .HasForeignKey(p => p.DeckForeignKey);
//        }
    }
}