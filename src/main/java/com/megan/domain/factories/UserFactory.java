package com.megan.domain.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import com.megan.domain.entities.SocialAuthAuthenticationInformation;
import com.megan.domain.entities.User;

@Named("megan-userFactory")
@Stateless
public class UserFactory {

	@Inject
	@Named
	private Neo4jTemplate neoTemplate;

	public User create(final String socialAuthProviderId, final String socialAuthValidatedId, final User user) {
		this.get(socialAuthProviderId, socialAuthValidatedId, true);
		if (user.getNodeId() != null) {
			throw new RuntimeException("User must not be persisted already.");
		}

		try (Transaction tx = this.neoTemplate.getGraphDatabaseService().beginTx()) {
			final SocialAuthAuthenticationInformation authInfo = new SocialAuthAuthenticationInformation();
			authInfo.setCreated(DateTime.now());
			authInfo.setSocialAuthProviderId(socialAuthProviderId);
			authInfo.setSocialAuthValidatedId(socialAuthValidatedId);
			authInfo.persist();

			user.setAuthInfo(authInfo);
			user.persist();

			tx.success();
		}

		return user;
	}

	public User get(final String socialAuthProviderId, final String socialAuthValidatedId) {
		return this.get(socialAuthProviderId, socialAuthValidatedId, false);
	}

	public User get(final String socialAuthProviderId, final String socialAuthValidatedId, final boolean asureUnique) {
		final String query = "MATCH (authInfos:SocialAuthAuthenticationInformation {"
				+ (socialAuthProviderId != null ? "socialAuthProviderId:{sapId}, " : "")
				+ "socialAuthValidatedId:{savId}}) RETURN authInfos;";
		final Map<String, Object> params = MapUtil.map("sapId", socialAuthProviderId, "savId", socialAuthValidatedId);

		try (Transaction tx = this.neoTemplate.getGraphDatabaseService().beginTx()) {
			final GraphRepository<SocialAuthAuthenticationInformation> repo = this.neoTemplate
					.repositoryFor(SocialAuthAuthenticationInformation.class);
			final Result<SocialAuthAuthenticationInformation> result = repo.query(query, params);
			final Iterator<SocialAuthAuthenticationInformation> iter = result.iterator();

			if (asureUnique) {
				final List<SocialAuthAuthenticationInformation> copy = new ArrayList<>();
				while (iter.hasNext() && copy.size() < 3) {
					final SocialAuthAuthenticationInformation element = iter.next();
					copy.add(element);
				}
				if (copy.size() > 1) {
					throw new RuntimeException("There should be max. 1 user for this state. providerId: "
							+ socialAuthProviderId + ", validatedId: " + socialAuthValidatedId);
				}

				return copy.isEmpty() ? null : copy.get(0).getUser();
			} else {
				return iter.hasNext() ? iter.next().getUser() : null;
			}
		}
	}

	public void setNeoTemplate(final Neo4jTemplate neoTemplate) {
		this.neoTemplate = neoTemplate;
	}
}
