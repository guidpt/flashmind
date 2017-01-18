using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Flashmind.Data.Context;
using Flashmind.Data.Entity;

namespace Flashmind.Data.Migrations
{
    [DbContext(typeof(FlashmindContext))]
    [Migration("20170104002922_1.0.0")]
    partial class _100
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "1.1.0-rtm-22752")
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("Flashmind.Data.Entity.CardEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("AnswerImageUrl");

                    b.Property<string>("AnswerString");

                    b.Property<Guid>("DeckForeignKey");

                    b.Property<string>("QuestionImageUrl");

                    b.Property<string>("QuestionString");

                    b.HasKey("Id");

                    b.HasIndex("DeckForeignKey");

                    b.ToTable("Cards");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckCardRoundEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<Guid>("CardForeignKey");

                    b.Property<Guid>("DeckRoundForeignKey");

                    b.Property<bool>("IsCorrect");

                    b.HasKey("Id");

                    b.HasIndex("CardForeignKey");

                    b.HasIndex("DeckRoundForeignKey");

                    b.ToTable("DeckCardRounds");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Color");

                    b.Property<int>("Privacy");

                    b.Property<string>("Tags");

                    b.Property<Guid>("UserForeignKey");

                    b.HasKey("Id");

                    b.HasIndex("UserForeignKey");

                    b.ToTable("Decks");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckRoundEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<Guid>("DeckForeignKey");

                    b.Property<double>("Score");

                    b.HasKey("Id");

                    b.HasIndex("DeckForeignKey");

                    b.ToTable("DeckRounds");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.JoinedDeckEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<Guid>("DeckForeignKey");

                    b.Property<Guid>("UserForeignKey");

                    b.HasKey("Id");

                    b.HasIndex("DeckForeignKey");

                    b.HasIndex("UserForeignKey");

                    b.ToTable("JoinedDecks");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.UserEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ExternalId");

                    b.Property<bool>("IsRegistred");

                    b.Property<string>("Username");

                    b.HasKey("Id");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.CardEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.DeckEntity", "Deck")
                        .WithMany("Cards")
                        .HasForeignKey("DeckForeignKey")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckCardRoundEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.CardEntity", "Card")
                        .WithMany()
                        .HasForeignKey("CardForeignKey")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("Flashmind.Data.Entity.DeckRoundEntity", "Round")
                        .WithMany("DeckCardRounds")
                        .HasForeignKey("DeckRoundForeignKey")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.UserEntity", "User")
                        .WithMany("Decks")
                        .HasForeignKey("UserForeignKey")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckRoundEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.DeckEntity", "Deck")
                        .WithMany()
                        .HasForeignKey("DeckForeignKey")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.JoinedDeckEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.DeckEntity", "Deck")
                        .WithMany()
                        .HasForeignKey("DeckForeignKey")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("Flashmind.Data.Entity.UserEntity", "User")
                        .WithMany("JoinedDecks")
                        .HasForeignKey("UserForeignKey")
                        .OnDelete(DeleteBehavior.Cascade);
                });
        }
    }
}
