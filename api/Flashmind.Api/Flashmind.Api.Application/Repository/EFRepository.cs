using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace Flashmind.Api.Application.Repository
{
    public class EfRepository<T> : IDataRepository<T> where T : class
    {
        protected DbContext _dataContext;

        public EfRepository(DbContext dataContext)
        {
            _dataContext = dataContext;
        }

        public virtual void Add(T entity)
        {
            _dataContext.Set<T>().Add(entity);
        }

        public virtual async Task<int> AddAndSaveAsync(T entity)
        {
            this.Add(entity);

            return await _dataContext.SaveChangesAsync();
        }

        public virtual void AttachAndUpdate(T entity)
        {
            _dataContext.Entry(entity).State = EntityState.Modified;
            _dataContext.Set<T>().Attach(entity);
        }

        public virtual async Task<int> AttachUpdateAndSaveAsync(T entity)
        {
            this.AttachAndUpdate(entity);

            return await _dataContext.SaveChangesAsync();
        }

        public virtual void Update(T entity)
        {
            _dataContext.Entry(entity).State = EntityState.Modified;
        }

        public virtual async Task<int> UpdateAndSaveAsync(T entity)
        {
            this.Update(entity);

            return await _dataContext.SaveChangesAsync();
        }

        public virtual void Remove(T entity)
        {
            _dataContext.Set<T>().Remove(entity);
        }

        public virtual async Task<int> RemoveAndSaveAsync(T entity)
        {
            this.Remove(entity);

            return await _dataContext.SaveChangesAsync();
        }

        public virtual async Task<int> SaveAsync()
        {
            return await _dataContext.SaveChangesAsync();
        }

        public virtual async Task<T> SingleOrDefaultAsync(Expression<Func<T, bool>> predicate)
        {
            return await this.Where(predicate).SingleOrDefaultAsync();
        }

        public virtual async Task<T> FirstOrDefaultAsync(Expression<Func<T, bool>> predicate)
        {
            return await this.Where(predicate).FirstOrDefaultAsync();
        }

        public virtual async Task<List<T>> ToListAsync()
        {
            return await GetAll().ToListAsync();
        }

        public virtual async Task<List<T>> ToListAsync(Expression<Func<T, bool>> predicate)
        {
            return await this.Where(predicate).ToListAsync();
        }

        public virtual async Task<int> CountAsync()
        {
            return await this.GetAll().CountAsync();
        }

        public virtual async Task<int> CountAsync(Expression<Func<T, bool>> predicate)
        {
            return await this.Where(predicate).CountAsync();
        }

        public virtual IQueryable<T> Where(Expression<Func<T, bool>> predicate)
        {
            return _dataContext.Set<T>().Where(predicate);
        }

        public virtual IQueryable<T> GetAll()
        {
            return _dataContext.Set<T>();
        }

        public virtual async Task ClearAsnyc()
        {
            foreach (var item in await this.GetAll().ToListAsync())
            {
                this.Remove(item);
            }
        }

        public virtual async Task ClearAndSaveAsnyc()
        {
            foreach (var item in await this.GetAll().ToListAsync())
            {
                this.Remove(item);
            }

            await this.SaveAsync();
        }

        #region IDisposable

        private bool disposed = false;

        protected virtual void Dispose(bool disposing)
        {
            if (!this.disposed)
            {
                if (disposing)
                {
                    this._dataContext.Dispose();
                }
            }

            this.disposed = true;
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        #endregion
    }
}