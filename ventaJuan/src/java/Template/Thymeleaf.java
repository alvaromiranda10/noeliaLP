package Template;

import javax.servlet.ServletContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class Thymeleaf {

    private final TemplateEngine template;

    public Thymeleaf(ServletContext ctx) {

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(ctx);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");

        // tiempo en cache
        templateResolver.setCacheTTLMs(3600000L);

        // desactivar la cache, true para activarla
        templateResolver.setCacheable(false);

        template = new TemplateEngine();
        template.setTemplateResolver(templateResolver);
    }

    public TemplateEngine getTemplateEngine() {
        return template;
    }
}
