package share.apk.server.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class SocialCredential {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
