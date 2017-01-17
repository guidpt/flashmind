using System;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Api.Data.Entity
{
    public class DeckEntity : BaseEntity
    {
        public string Tags { get; set; }

        public PrivacyEnum Privacy { get; set; }

        public string Color { get; set; }

        public Guid UserForeignKey { get; set; }

        //Virtual
        public UserEntity User { get; set; }



        public const string TableName = "Users";

        public static void Setup(ModelBuilder builder)
        {
            builder.Entity<DeckEntity>().ToTable(TableName);

            builder.Entity<DeckEntity>()
                .HasOne(u => u.User)
                .WithMany(b => b.Decks)
                .HasForeignKey(p => p.UserForeignKey);
        }
    }

    public enum PrivacyEnum
    {
        Private,
        Public
    }
}