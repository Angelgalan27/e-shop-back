package es.eshop.app.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class Utils {

    /**
     *  get user context
     * @return
     */
    public static User getUserContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Get pageable.
     *
     * @param page
     * @param limit
     * @param sort
     * @param sortOrden
     * @return
     */
    public static Pageable getPageable(Integer page, Integer limit, String sort, String sortOrden) {
        Pageable pageable;
        page = page != null ? page : 0;
        limit = limit != null ? limit : 10;
        if (StringUtils.isEmpty(sort)) {
            pageable = PageRequest.of(page, limit);
        } else if(!StringUtils.isEmpty(sortOrden) && sortOrden.equals("asc")){
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }
        return pageable;
    }
}
