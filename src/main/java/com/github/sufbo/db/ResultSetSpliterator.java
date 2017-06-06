package com.github.sufbo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * このクラスにある {@link #stream <code>stream</code>}メソッドを使って，Stream を取得すること．
 * そうでない場合，終了後，PreparedStatement が閉じられないため，リソースリークの可能性がある．
 *
 * @see http://fits.hatenablog.com/entry/2015/10/26/211731 を少し改良．
 */
public class ResultSetSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
    private TryFunction<ResultSet, T, SQLException> converter;

    private PreparedStatement ps;
    private ResultSet rs;

    public ResultSetSpliterator(PreparedStatement ps, TryFunction<ResultSet, T, SQLException> function) throws SQLException{
        super(Long.MAX_VALUE, NONNULL);
        this.ps = ps;
        this.rs = ps.executeQuery();
        this.converter = function;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        try{
            if(rs.next()){
                action.accept(converter.apply(rs));
                return true;
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public Stream<T> stream(){
        return StreamSupport.stream(this, false).onClose(() -> {
            try{
                ps.close();   
            } catch(SQLException e){
                throw new InternalError(e);
            }
        });
    }
}
