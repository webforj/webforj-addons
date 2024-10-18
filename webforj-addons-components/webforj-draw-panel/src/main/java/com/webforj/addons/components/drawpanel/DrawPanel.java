package com.webforj.addons.components.drawpanel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.webforj.bridge.WindowAccessor;
import com.webforj.component.window.Window;
import com.webforj.exceptions.WebforjRuntimeException;

import com.basis.bbj.proxies.sysgui.BBjDrawPanel;
import com.basis.bbj.proxies.sysgui.BBjImage;
import com.basis.bbj.proxies.sysgui.BBjWindow;
import com.basis.startup.type.BBjException;
import com.basis.startup.type.sysgui.BBjColor;
import com.basis.util.common.BasisNumber;

import com.webforj.App;

public final class DrawPanel extends Window {

	private BBjDrawPanel drawPanel;
	private Window window;
	private boolean scribble;
	private Color penColor;
	private int penWidth;
	private int opacity;
	private double width;
	private double height;

	public DrawPanel() {
		scribble = true;
		penColor = new Color(0, 0, 0);
		penWidth = 1;
		opacity = 1;
		width = 1;
		height = 1;
	}

	public DrawPanel(double startWidth, double startHeight) {
		this();
		width = startWidth;
		height = startHeight;
	}

	@Override
	protected void onCreate(Window window) {
		super.onCreate(window);
		this.window = window;
		try {
			BBjWindow w = WindowAccessor.getDefault().getBBjWindow(window);
			w.setSize(new BasisNumber(width), new BasisNumber(height));
			this.drawPanel = w.getDrawPanel();
			setScribble(scribble);
			setOpaque(opacity);
			setPenColor(penColor);
			setPenWidth(penWidth);
		} catch (BBjException | IllegalAccessException e) {
			throw new WebforjRuntimeException(e);
		}
	}

	public BBjImage getDrawPanelBBjImage() {
		if (drawPanel != null) {
			try {
				return drawPanel.getDrawPanelImage();
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return null;
	}

	public BufferedImage getDrawPanelImage() {
		if (drawPanel != null) {
			try {
				ByteArrayInputStream byteStream = new ByteArrayInputStream(
						drawPanel.getDrawPanelImage().getBytes("png"));
				return ImageIO.read(byteStream);
			} catch (BBjException | IOException e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return null;
	}

	public void setDrawPanelImage(BBjImage image) {
		if (drawPanel != null) {
			try {
				drawPanel.drawImage(image, new BasisNumber(0), new BasisNumber(0),
						new BasisNumber(width), new BasisNumber(height));
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
	}

	public DrawPanel setDimensions(double width, double height) {
		this.width = width;
		this.height = height;
		if (drawPanel != null) {
			try {
				BBjWindow w = WindowAccessor.getDefault().getBBjWindow(window);
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

	public DrawPanel setOpaque(int opacity) {
		this.opacity = opacity;
		if (drawPanel != null) {
			try {
				drawPanel.setOpaque(opacity);
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

	public DrawPanel setPenColor(Color penColor) {
		this.penColor = penColor;
		if (drawPanel != null) {
			try {
				drawPanel.setPenColor(new BBjColor(penColor));
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

	public DrawPanel setPenWidth(int penWidth) {
		this.penWidth = penWidth;
		if (drawPanel != null) {
			try {
				drawPanel.setPenWidth(new BasisNumber(penWidth));
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

	public DrawPanel clearDrawing() {
		if (drawPanel != null) {
			try {
				drawPanel.clearDrawing();
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

	public DrawPanel setScribble(boolean scribble) {
		if (drawPanel != null) {
			try {
				drawPanel.setScribble(scribble);
				return this;
			} catch (BBjException e) {
				throw new WebforjRuntimeException(e);
			}
		}
		this.scribble = scribble;
		return this;
	}

}