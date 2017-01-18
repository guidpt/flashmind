using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace Flashmind.Api.Application.Repository
{
    public interface IDataRepository<T> : IDisposable where T : class
    {
        void Add(T entity);

        Task<int> AddAndSaveAsync(T entity);

        void AttachAndUpdate(T entity);

        Task<int> AttachUpdateAndSaveAsync(T entity);

        void Update(T entity);

        Task<int> UpdateAndSaveAsync(T entity);

        void Remove(T entity);

        Task<int> RemoveAndSaveAsync(T entity);

        Task<int> SaveAsync();

        Task<T> SingleOrDefaultAsync(Expression<Func<T, bool>> predicate);

        Task<T> FirstOrDefaultAsync(Expression<Func<T, bool>> predicate);

        Task<List<T>> ToListAsync();

        Task<List<T>> ToListAsync(Expression<Func<T, bool>> predicate);

        Task<int> CountAsync();

        Task<int> CountAsync(Expression<Func<T, bool>> predicate);

        IQueryable<T> Where(Expression<Func<T, bool>> predicate);

        IQueryable<T> GetAll();

        Task ClearAsnyc();

        Task ClearAndSaveAsnyc();
    }
}