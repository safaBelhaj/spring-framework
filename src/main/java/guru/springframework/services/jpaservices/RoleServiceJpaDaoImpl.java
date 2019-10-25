package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.security.Role;
import guru.springframework.services.RoleService;

@Profile("jpadao")
@Service
public class RoleServiceJpaDaoImpl  extends AbstractJpaDaoService implements RoleService{

	@Override
	public List<Role> listAll() {
		EntityManager em =emf.createEntityManager();
		return em.createQuery("from Role",Role.class).getResultList();
	}

	@Override
	public Role getById(Integer id) {
		EntityManager em =emf.createEntityManager();
		return em.find(Role.class, id);
	}

	@Override
	public Role saveOrUpdate(Role domainObject) {
		EntityManager em =emf.createEntityManager();
		em.getTransaction().begin();
		Role savedRole=em.merge(domainObject);
		em.getTransaction().commit();
		return savedRole;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em =emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Role.class,id));
	}

}
