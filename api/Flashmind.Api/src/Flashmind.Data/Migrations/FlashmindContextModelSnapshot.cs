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
    partial class FlashmindContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
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

                    b.Property<Guid>("DeckId");

                    b.Property<string>("QuestionImageUrl");

                    b.Property<string>("QuestionString");

                    b.HasKey("Id");

                    b.HasIndex("DeckId");

                    b.ToTable("Cards");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckCardRoundEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<Guid>("CardId");

                    b.Property<Guid>("DeckRoundId");

                    b.Property<bool>("IsCorrect");

                    b.HasKey("Id");

                    b.HasIndex("CardId");

                    b.HasIndex("DeckRoundId");

                    b.ToTable("DeckCardRounds");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<double>("BestScore");

                    b.Property<string>("Color");

                    b.Property<DateTime>("CreatedDate");

                    b.Property<int>("DownloadCount");

                    b.Property<bool>("IsBan");

                    b.Property<string>("Language");

                    b.Property<DateTime>("LastUpdateDate");

                    b.Property<string>("Name");

                    b.Property<int>("PlayedRounds");

                    b.Property<int>("Privacy");

                    b.Property<int>("Reports");

                    b.Property<string>("Tags");

                    b.Property<int>("TotalCards");

                    b.Property<Guid>("UserId");

                    b.HasKey("Id");

                    b.HasIndex("UserId");

                    b.ToTable("Decks");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckRoundEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<Guid>("DeckId");

                    b.Property<double>("Score");

                    b.HasKey("Id");

                    b.HasIndex("DeckId");

                    b.ToTable("DeckRounds");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.JoinedDeckEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<Guid>("DeckId");

                    b.Property<Guid>("UserId");

                    b.HasKey("Id");

                    b.HasIndex("DeckId");

                    b.HasIndex("UserId");

                    b.ToTable("JoinedDecks");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.UserEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ExternalId");

                    b.Property<bool>("IsRegistred");

                    b.Property<string>("Name");

                    b.Property<string>("Password");

                    b.Property<string>("Token");

                    b.Property<string>("Username");

                    b.HasKey("Id");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("Flashmind.Data.Entity.CardEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.DeckEntity", "Deck")
                        .WithMany("Cards")
                        .HasForeignKey("DeckId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckCardRoundEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.CardEntity", "Card")
                        .WithMany()
                        .HasForeignKey("CardId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("Flashmind.Data.Entity.DeckRoundEntity", "Round")
                        .WithMany("DeckCardRounds")
                        .HasForeignKey("DeckRoundId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.UserEntity", "User")
                        .WithMany("Decks")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.DeckRoundEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.DeckEntity", "Deck")
                        .WithMany()
                        .HasForeignKey("DeckId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Flashmind.Data.Entity.JoinedDeckEntity", b =>
                {
                    b.HasOne("Flashmind.Data.Entity.DeckEntity", "Deck")
                        .WithMany()
                        .HasForeignKey("DeckId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("Flashmind.Data.Entity.UserEntity", "User")
                        .WithMany("JoinedDecks")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });
        }
    }
}
