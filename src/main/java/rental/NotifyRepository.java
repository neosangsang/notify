package rental;

import org.springframework.data.repository.PagingAndSortingRepository;
public interface NotifyRepository extends PagingAndSortingRepository<Message, Long> {
}
