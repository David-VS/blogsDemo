package be.ehb.blogify.dao;

import be.ehb.blogify.model.BlogPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogDAO extends CrudRepository<BlogPost, Integer> {

    @Query("SELECT b FROM BlogPost b ORDER BY b.dateCreated DESC ")
    List<BlogPost> findAllChronological();


}
