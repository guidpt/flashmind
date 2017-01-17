using System;

namespace Flashmind.Api.Data.Entity
{
    public class CardEntity : BaseEntity
    {

        public string QuestionString { get; set; }

        public string QuestionImageUrl { get; set; }

        public string AnswerString { get; set; }

        public string AnswerImageUrl { get; set; }

        public Guid DeckForeignKey { get; set; }

        //Virtual
        public DeckEntity Deck { get; set; }

    }
}