/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.examples.authenticator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialTypeMetadata;
import org.keycloak.credential.CredentialTypeMetadataContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class SecretQuestionCredentialProvider
		implements CredentialProvider<CredentialModel>, CredentialInputValidator, CredentialInputUpdater {
	public static final String SECRET_QUESTION = "SECRET_QUESTION";
	public static final String CACHE_KEY = SecretQuestionCredentialProvider.class.getName() + "." + SECRET_QUESTION;

	protected KeycloakSession session;

	public SecretQuestionCredentialProvider(KeycloakSession session) {
		this.session = session;
	}

	public CredentialModel getSecret(RealmModel realm, UserModel user) {
		CredentialModel secret = null;
		List<CredentialModel> creds = user.credentialManager().getStoredCredentialsByTypeStream(SECRET_QUESTION)
				.collect(Collectors.toList());
		if (!creds.isEmpty())
			secret = creds.get(0);
		return secret;
	}

	@Override
	public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
		if (!SECRET_QUESTION.equals(input.getType()))
			return false;
		if (!(input instanceof UserCredentialModel))
			return false;
		UserCredentialModel credInput = (UserCredentialModel) input;
		List<CredentialModel> creds = user.credentialManager().getStoredCredentialsByTypeStream(SECRET_QUESTION)
				.collect(Collectors.toList());
		if (creds.isEmpty()) {
			CredentialModel secret = new CredentialModel();
			secret.setType(SECRET_QUESTION);
			secret.setSecretData(credInput.getChallengeResponse());
			secret.setCreatedDate(Time.currentTimeMillis());
			user.credentialManager().createStoredCredential(secret);
		} else {
			creds.get(0).setCredentialData(credInput.getChallengeResponse());
			user.credentialManager().createStoredCredential(creds.get(0));
		}
		return true;
	}

	@Override
	public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
		if (!SECRET_QUESTION.equals(credentialType))
			return;
		user.credentialManager().disableCredentialType(credentialType);

	}

	@Override
	@SuppressWarnings("unchecked")
	public Stream<String> getDisableableCredentialTypesStream(RealmModel realm, UserModel user) {
		if (!user.credentialManager().getStoredCredentialsByTypeStream(SECRET_QUESTION).collect(Collectors.toList())
				.isEmpty()) {
			Set<String> set = new HashSet<>();
			set.add(SECRET_QUESTION);
			return set.stream();
		} else {
			return Collections.EMPTY_SET.stream();
		}

	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		return SECRET_QUESTION.equals(credentialType);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		if (!SECRET_QUESTION.equals(credentialType))
			return false;
		return getSecret(realm, user) != null;
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		if (!SECRET_QUESTION.equals(input.getType()))
			return false;
		if (!(input instanceof UserCredentialModel))
			return false;

		String secret = getSecret(realm, user).getSecretData();

		return secret != null && ((UserCredentialModel) input).getChallengeResponse().equals(secret);
	}

	@Override
	public String getType() {
		return SECRET_QUESTION;
	}

	@Override
	public CredentialModel createCredential(RealmModel realm, UserModel user, CredentialModel credentialModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCredential(RealmModel realm, UserModel user, String credentialId) {
		return false;
	}

	@Override
	public CredentialModel getCredentialFromModel(CredentialModel model) {
		return model;
	}

	@Override
	public CredentialTypeMetadata getCredentialTypeMetadata(CredentialTypeMetadataContext metadataContext) {
		// TODO Auto-generated method stub
		return null;
	}
}
