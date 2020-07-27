package eth.craig.alert0x.repository.eventeum;

import java.util.Collections;
import java.util.Optional;
import net.consensys.eventeum.dto.event.filter.ContractEventFilter;
import org.springframework.data.repository.CrudRepository;

/**
 * As we load from the scripts folder on every startup, there's no need to store
 * in a database.  This repository does nothing.
 *
 * @author Craig Williams
 */
public class DoNothingRepository<T> implements CrudRepository<T, String> {

    @Override
    public <S extends T> S save(S s) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<T> findById(String s) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return Collections.emptySet();
    }

    @Override
    public Iterable<T> findAllById(Iterable<String> strings) {
        return Collections.emptySet();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(T contractEventFilter) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
