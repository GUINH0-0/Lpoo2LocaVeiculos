package model.dao;

public class DaoFactory {
    private DaoFactory() { }

    @SuppressWarnings("unchecked")
    public static <T> T getDao(Class<T> daoClass, DaoType type) {
        if (type != DaoType.SQL) {
            throw new RuntimeException("Tipo não suportado: " + type);
        }

        // Retorna instâncias singleton dos DAOs SQL
        if (daoClass == VeiculoDao.class) {
            return (T) VeiculoDaoSql.getInstance();       // singleton do VeiculoDaoSql
        } else if (daoClass == ClienteDao.class) {
            return (T) ClienteDaoSql.getInstance();       // singleton do ClienteDaoSql
        } else if (daoClass == LocacaoDao.class) {
            return (T) LocacaoDaoSql.getInstance();       // singleton do LocacaoDaoSql
        }

        throw new RuntimeException("DAO não existe para: " + daoClass.getSimpleName());
    }
}
