using System;
using System.ComponentModel.DataAnnotations;

namespace Flashmind.Data.Entity
{
    public class BaseEntity
    {

        [Key]
        public Guid Id { get; set; }
    }
}