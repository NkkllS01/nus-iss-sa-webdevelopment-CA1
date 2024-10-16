package sg.edu.nus.ophone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import sg.edu.nus.ophone.model.Brand;

//code by Team3.Ng Jiamin
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
