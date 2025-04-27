package chan.shop.commonservice.outboxmessagerelay;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    // 전송되지 않은 이벤트들을 주기적으로 polling 하기위한 이벤트 조회
    List<Outbox> findAllByShardKeyAndCreateAtLessThanEqualOrderByCreateAtAsc(
            Long shardKey,
            LocalDateTime from,
            Pageable pageable
    );
}
