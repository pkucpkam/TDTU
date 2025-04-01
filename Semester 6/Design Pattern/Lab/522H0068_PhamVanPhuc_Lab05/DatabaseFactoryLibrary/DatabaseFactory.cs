using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DatabaseFactoryLibrary
{
    public abstract class DatabaseFactory
    {
        public abstract DbParameter CreateParameter(string name, object value);
        public abstract DbCommand CreateCommand();
        public abstract DbCommand CreateCommand(string cmdText);
        public abstract DbCommand CreateCommand(string cmdText, DbConnection cn);
        public abstract DbConnection CreateConnection();
        public abstract DbConnection CreateConnection(string cnString);
        public abstract DbDataAdapter CreateDataAdapter();
        public abstract DbDataAdapter CreateDataAdapter(DbCommand selectCmd);
        public abstract DbDataReader CreateDataReader(DbCommand dbCmd);
    }

}
