package PW;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;

public class ProgressPullPage extends BasePage {
	private static final long serialVersionUID = 1L;

	protected String sProg = "0%";

	public ProgressPullPage(final PageParameters parameters) {
		super(parameters);

		// add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		final WebMarkupContainer contPullProg;
		add(contPullProg = new WebMarkupContainer("contPullProg"));
		contPullProg.setOutputMarkupId(true);

		WebMarkupContainer prog;
		contPullProg.add( prog = new WebMarkupContainer("prog"));
		prog.add( new AttributeModifier("style", new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return "width: " + sProg;
			}
		}));

		contPullProg.add( new Label("lblProg", new PropertyModel<String>(this, "sProg")));

		contPullProg.add( new AjaxSelfUpdatingTimerBehavior(Duration.milliseconds(250)) {
			@Override
			protected void onPostProcessTarget(AjaxRequestTarget target) {
				super.onPostProcessTarget(target);
				if (target != null) {
					target.add(contPullProg);
				}
			}
		});

		new Thread() {
			@Override
			public void run() {
				for (int i=0; i <= 100; i++) {
					try {
						sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					sProg = String.valueOf(i) + "%";
				}
			}
		}.start();
    }
}
