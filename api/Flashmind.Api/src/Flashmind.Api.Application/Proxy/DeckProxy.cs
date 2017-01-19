using System;
using System.Collections.Generic;
using Flashmind.Data.Entity;

namespace Flashmind.Api.Application.Proxy
{
    public class DeckProxy
    {
        public string Name { get; set; }

        public string Tags { get; set; }

        public IEnumerable<string> ListTags { get; set; }

        public PrivacyEnum Privacy { get; set; }

        public string Color { get; set; }

        public Guid UserId { get; set; }

        public string UserName { get; set; }

        public DateTime CreatedDate { get; set; }

        public DateTime LastUpdateDate { get; set; }

        public double BestScore { get; set; }

        public int PlayedRounds { get; set; }

        public int TotalCards { get; set; }

        public int DownloadCount { get; set; }
    }
}