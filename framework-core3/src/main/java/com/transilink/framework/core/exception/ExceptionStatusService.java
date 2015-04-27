package com.transilink.framework.core.exception;

import org.restlet.data.CharacterSet;
import org.restlet.data.Language;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;
import org.restlet.service.StatusService;

import com.transilink.framework.core.logs.LogEnabled;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class ExceptionStatusService extends StatusService implements LogEnabled {
	public Representation getRepresentation(Status status, Request request,
			Response response) {
		if (status != null) {
			Throwable throwable = status.getThrowable();

			if (throwable != null) {
				try {
					if ((throwable instanceof SysException)) {
						String message = throwable.getMessage();
						log.error(message);
						return new StringRepresentation(message,
								MediaType.APPLICATION_JSON, Language.ALL,
								CharacterSet.UTF_8);
					}

					String defautMessage = status.getDescription();
					log.error(defautMessage);
					return new StringRepresentation(defautMessage,
							MediaType.APPLICATION_JSON, Language.ALL,
							CharacterSet.UTF_8);
				} catch (Exception e) {
				}
			} else if (status.getCode() / 100 == 4) {
				String defautMessage = status.getDescription();
				log.error(defautMessage);
				return new StringRepresentation(defautMessage,
						MediaType.APPLICATION_JSON, Language.ALL,
						CharacterSet.UTF_8);
			}

		}

		return super.getRepresentation(status, request, response);
	}
}