package PW;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		// add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		add(new BookmarkablePageLink<Void>("lnkPullProgress", ProgressPullPage.class));
    }
}
