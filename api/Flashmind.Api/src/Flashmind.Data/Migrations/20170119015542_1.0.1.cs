using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace Flashmind.Data.Migrations
{
    public partial class _101 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "IsBan",
                table: "Decks",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "Language",
                table: "Decks",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Reports",
                table: "Decks",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "IsBan",
                table: "Decks");

            migrationBuilder.DropColumn(
                name: "Language",
                table: "Decks");

            migrationBuilder.DropColumn(
                name: "Reports",
                table: "Decks");
        }
    }
}
