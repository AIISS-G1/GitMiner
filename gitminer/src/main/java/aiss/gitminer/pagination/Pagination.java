package aiss.gitminer.pagination;

import aiss.gitminer.exception.PageSizeTooLargeException;
import org.springframework.data.domain.PageRequest;

public class Pagination {

    public static final int MAX_SIZE = 100;

    public static PageRequest of(int page, int size) {
        if (size > MAX_SIZE)
            throw new PageSizeTooLargeException();
        return PageRequest.of(page, size);
    }
}
