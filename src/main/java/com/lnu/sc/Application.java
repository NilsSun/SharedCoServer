package com.lnu.sc;

import com.lnu.sc.config.RestConstants;
import com.lnu.sc.data.RepositoryData;
import com.lnu.sc.entities.Artifact;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;

@ComponentScan
@EnableAutoConfiguration
public class Application {
	
	@Value("${keystore.file}") private String keystoreFile;
	@Value("${keystore.pass}") private String keystorePass;
        
        public static List<Artifact> artifacts = RepositoryData.getArtifact();
        
    public static void main(String[] args) {
        
        File[] folder1 = new File(RestConstants.PATH_ONE).listFiles();
        int i = 0;
        for (File f : folder1) {
            // f is only file path string
            artifacts.add(new Artifact(i, f));
            //artifacts_backup.add(new Artifact(i, f));
            i++;
        }
        
        SpringApplication.run(Application.class, args);
    }
    
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer()  throws FileNotFoundException {
		final String absoluteKeystoreFile = ResourceUtils.getFile(keystoreFile).getAbsolutePath();

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(	ConfigurableEmbeddedServletContainer factory) {
				if (factory instanceof TomcatEmbeddedServletContainerFactory) {
					TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
					containerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
							@Override
							public void customize(Connector connector) {
								connector.setPort(8443);
								connector.setSecure(true);
								connector.setScheme("https");
								Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
					            proto.setSSLEnabled(true);
					            proto.setKeystoreFile(absoluteKeystoreFile);
					            proto.setKeystorePass(keystorePass);
					            proto.setKeystoreType("PKCS12");
					            proto.setKeyAlias("tomcat");
							}
						});
				}
			}
		};
	}
}
