package aiss.gitminer.exception;

import aiss.gitminer.pagination.Pagination;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "pageSize argument too large: max is " + Pagination.MAX_SIZE)
public class PageSizeTooLargeException extends RuntimeException {
}
