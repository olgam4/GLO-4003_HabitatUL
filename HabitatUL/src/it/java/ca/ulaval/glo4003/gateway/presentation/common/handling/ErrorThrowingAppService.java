package ca.ulaval.glo4003.gateway.presentation.common.handling;

public interface ErrorThrowingAppService {
  Throwable getError() throws Exception;
}
