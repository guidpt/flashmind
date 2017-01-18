using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Flashmind.Data.Migrations
{
    public partial class _100 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    ExternalId = table.Column<string>(nullable: true),
                    IsRegistred = table.Column<bool>(nullable: false),
                    Username = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Decks",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    Color = table.Column<string>(nullable: true),
                    Privacy = table.Column<int>(nullable: false),
                    Tags = table.Column<string>(nullable: true),
                    UserForeignKey = table.Column<Guid>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Decks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Decks_Users_UserForeignKey",
                        column: x => x.UserForeignKey,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Cards",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    AnswerImageUrl = table.Column<string>(nullable: true),
                    AnswerString = table.Column<string>(nullable: true),
                    DeckForeignKey = table.Column<Guid>(nullable: false),
                    QuestionImageUrl = table.Column<string>(nullable: true),
                    QuestionString = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Cards", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Cards_Decks_DeckForeignKey",
                        column: x => x.DeckForeignKey,
                        principalTable: "Decks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "DeckRounds",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    DeckForeignKey = table.Column<Guid>(nullable: false),
                    Score = table.Column<double>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DeckRounds", x => x.Id);
                    table.ForeignKey(
                        name: "FK_DeckRounds_Decks_DeckForeignKey",
                        column: x => x.DeckForeignKey,
                        principalTable: "Decks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "JoinedDecks",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    DeckForeignKey = table.Column<Guid>(nullable: false),
                    UserForeignKey = table.Column<Guid>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_JoinedDecks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_JoinedDecks_Decks_DeckForeignKey",
                        column: x => x.DeckForeignKey,
                        principalTable: "Decks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_JoinedDecks_Users_UserForeignKey",
                        column: x => x.UserForeignKey,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "DeckCardRounds",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    CardForeignKey = table.Column<Guid>(nullable: false),
                    DeckRoundForeignKey = table.Column<Guid>(nullable: false),
                    IsCorrect = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DeckCardRounds", x => x.Id);
                    table.ForeignKey(
                        name: "FK_DeckCardRounds_Cards_CardForeignKey",
                        column: x => x.CardForeignKey,
                        principalTable: "Cards",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_DeckCardRounds_DeckRounds_DeckRoundForeignKey",
                        column: x => x.DeckRoundForeignKey,
                        principalTable: "DeckRounds",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Cards_DeckForeignKey",
                table: "Cards",
                column: "DeckForeignKey");

            migrationBuilder.CreateIndex(
                name: "IX_DeckCardRounds_CardForeignKey",
                table: "DeckCardRounds",
                column: "CardForeignKey");

            migrationBuilder.CreateIndex(
                name: "IX_DeckCardRounds_DeckRoundForeignKey",
                table: "DeckCardRounds",
                column: "DeckRoundForeignKey");

            migrationBuilder.CreateIndex(
                name: "IX_Decks_UserForeignKey",
                table: "Decks",
                column: "UserForeignKey");

            migrationBuilder.CreateIndex(
                name: "IX_DeckRounds_DeckForeignKey",
                table: "DeckRounds",
                column: "DeckForeignKey");

            migrationBuilder.CreateIndex(
                name: "IX_JoinedDecks_DeckForeignKey",
                table: "JoinedDecks",
                column: "DeckForeignKey");

            migrationBuilder.CreateIndex(
                name: "IX_JoinedDecks_UserForeignKey",
                table: "JoinedDecks",
                column: "UserForeignKey");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "DeckCardRounds");

            migrationBuilder.DropTable(
                name: "JoinedDecks");

            migrationBuilder.DropTable(
                name: "Cards");

            migrationBuilder.DropTable(
                name: "DeckRounds");

            migrationBuilder.DropTable(
                name: "Decks");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
