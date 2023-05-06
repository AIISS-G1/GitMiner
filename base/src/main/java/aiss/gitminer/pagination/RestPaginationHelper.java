package aiss.gitminer.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RestPaginationHelper {

    public static <T> List<T> unwrap(Function<Integer, List<T>> supplierFunction, int maxPages) {
        List<T> content = new ArrayList<>();
        for (int page = 1; page <= maxPages; page++) {
            List<T> pageContents = supplierFunction.apply(page);
            if (pageContents.isEmpty())
                break;

            content.addAll(pageContents);
        }

        return content;
    }
}
