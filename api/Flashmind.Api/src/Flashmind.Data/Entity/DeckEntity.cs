using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Data.Entity
{
    public class DeckEntity : BaseEntity
    {
        public string Name { get; set; }

        public string Tags { get; set; }

        public PrivacyEnum Privacy { get; set; }

        public string Color { get; set; }

        public Guid UserForeignKey { get; set; }

        public int DownloadCount { get; set; }

        public DateTime LastUpdate { get; set; }

        public double BestScore { get; set; }

        public int PlayedRounds { get; set; }

        //Virtual
        [ForeignKey("UserForeignKey")]
        public UserEntity User { get; set; }

        public List<CardEntity> Cards { get; set; }

    }

    public enum PrivacyEnum
    {
        Private,
        Public
    }
}