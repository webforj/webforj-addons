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

/**
 * Represents a draw panel component that allows users to enter draw on the panel.
 *
 * <p>
 * This class handles setting up the drawing canvas, managing pen properties, opacity, and dimensions, and
 * interacting with images on the drawing panel. It also allows toggling between scribble mode and clearing
 * drawings.
 * </p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 * <li>Pen customization (color, width)</li>
 * <li>Adjustable panel dimensions</li>
 * <li>Support for image export and import</li>
 * <li>Configurable opacity and scribble mode</li>
 * </ul>
 *
 * <p>This class should be used in conjunction with a {@link Window} to create an interactive drawing area.</p>
 *
 * @see Window
 * @see BBjDrawPanel
 *
 * @author MatthewHawkins
 * @since 24.12
 */
public final class DrawPanel extends Window {

  /**
   * The {@code BBjDrawPanel} object that represents the drawing canvas where graphics can be drawn.
   * It provides methods to manipulate the drawing surface such as drawing images and setting drawing attributes.
   */
  private BBjDrawPanel drawPanelCanvas;

  /**
   * The {@code Window} instance where the {@code DrawPanel} is displayed. This field is used to manage
   * the connection between the drawing panel and the parent window.
   */
  private Window window;

  /**
   * A flag that controls whether the drawing panel is in scribble mode or not. When enabled,
   * scribble mode allows freehand drawing on the panel.
   */
  private boolean scribble;

  /**
   * The {@code Color} object that defines the pen color used for drawing on the panel.
   * It defines the color for lines, arrows, and outlines.
   */
  private Color penColor;

  /**
   * The width of the pen used for drawing in pixels.
   */
  private int penWidth;

  /**
   * The opacity of the drawing panel. The value can be {@code 0}, {@code 1}, and {@code 2}.
   *
   * <ul>
   *   <li>{@code 0} - Transparent and not scaled</li>
   *   <li>{@code 1} - Opaque and not scaled</li>
   *   <li>{@code 2} - Opaque and scaled</li>
   * </ul>
   */
  private int opacity;

  /**
   * The width of the drawing panel in pixels. This determines the horizontal size of the panel.
   */
  private double width;

  /**
   * The height of the drawing panel in pixels. This determines the vertical size of the panel.
   */
  private double height;

  /**
   * Default constructor for {@code DrawPanel}.
   * Initializes the drawing panel with default settings, including:
   * <ul>
   * <li>Scribble mode enabled</li>
   * <li>Pen color set to black</li>
   * <li>Pen width set to 1 pixel</li>
   * <li>Opacity set to 1 (fully opaque)</li>
   * <li>Panel dimensions initialized to 1x1</li>
   * </ul>
   */
	public DrawPanel() {
		scribble = true;
		penColor = new Color(0, 0, 0);
		penWidth = 1;
		opacity = 1;
		width = 1;
		height = 1;
	}

  /**
   * Constructs a {@code DrawPanel} with specified dimensions.
   *
   * @param startWidth  The initial width of the drawing panel.
   * @param startHeight The initial height of the drawing panel.
   */
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
			this.drawPanelCanvas = w.getDrawPanel();
			setScribble(scribble);
			setOpaque(opacity);
			setPenColor(penColor);
			setPenWidth(penWidth);
		} catch (BBjException | IllegalAccessException e) {
			throw new WebforjRuntimeException(e);
		}
	}

  /**
   * Returns the {@code BBjImage} of the current drawing on the panel. This is the underlying image format
   * used by the BBjDrawPanel.
   *
   * @return The {@code BBjImage} of the drawing panel, or {@code null} if no image is available.
   */
	public BBjImage getDrawPanelBBjImage() {
		if (drawPanelCanvas != null) {
			try {
				return drawPanelCanvas.getDrawPanelImage();
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return null;
	}

  /**
   * Returns a {@code BufferedImage} of the current drawing on the panel.
   *
   * @return The {@code BufferedImage} of the drawing panel, or {@code null} if no image is available.
   */
	public BufferedImage getDrawPanelImage() {
		if (drawPanelCanvas != null) {
			try {
				ByteArrayInputStream byteStream = new ByteArrayInputStream(
						drawPanelCanvas.getDrawPanelImage().getBytes("png"));
				return ImageIO.read(byteStream);
			} catch (BBjException | IOException e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return null;
	}

  /**
   * Sets the provided {@code BBjImage} onto the drawing panel canvas.
   *
   * @param image The {@code BBjImage} to be drawn on the panel.
   */
	public void setDrawPanelImage(BBjImage image) {
		if (drawPanelCanvas != null) {
			try {
				drawPanelCanvas.drawImage(image, new BasisNumber(0), new BasisNumber(0),
						new BasisNumber(width), new BasisNumber(height));
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
	}

  /**
   * Sets the dimensions of the drawing panel.
   *
   * @param width  The new width for the drawing panel.
   * @param height The new height for the drawing panel.
   * @return The current {@code DrawPanel} instance for chaining calls.
   */
	public DrawPanel setDimensions(double width, double height) {
		this.width = width;
		this.height = height;
		if (drawPanelCanvas != null) {
			try {
				BBjWindow w = WindowAccessor.getDefault().getBBjWindow(window);
        w.setSize(new BasisNumber(width), new BasisNumber(height));
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

  /**
   * Sets the opacity of the drawing panel.
   *
   * <p>
   * Valid values are:
   * <ul>
   *   <li>{@code 0} - Transparent and not scaled</li>
   *   <li>{@code 1} - Opaque and not scaled</li>
   *   <li>{@code 2} - Opaque and scaled</li>
   * </ul>
   * </p>
   *
   * @param opacity The opacity value can be {@code 0}, {@code 1}, or {@code 2}.
   * @return The current {@code DrawPanel} instance for chaining calls.
   */
	public DrawPanel setOpaque(int opacity) {
		this.opacity = opacity;
		if (drawPanelCanvas != null) {
			try {
				drawPanelCanvas.setOpaque(opacity);
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

  /**
   * Sets the pen color for the drawing panel.
   *
   * @param penColor The {@code Color} to be used for the pen.
   * @return The current {@code DrawPanel} instance for chaining calls.
   */
	public DrawPanel setPenColor(Color penColor) {
		this.penColor = penColor;
		if (drawPanelCanvas != null) {
			try {
				drawPanelCanvas.setPenColor(new BBjColor(penColor));
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

  /**
   * Sets the pen width for drawing.
   *
   * @param penWidth The width of the pen in pixels.
   * @return The current {@code DrawPanel} instance for chaining calls.
   */
	public DrawPanel setPenWidth(int penWidth) {
		this.penWidth = penWidth;
		if (drawPanelCanvas != null) {
			try {
				drawPanelCanvas.setPenWidth(new BasisNumber(penWidth));
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

  /**
   * Clears the current drawing from the panel.
   *
   * @return The current {@code DrawPanel} instance for chaining calls.
   */
	public DrawPanel clearDrawing() {
		if (drawPanelCanvas != null) {
			try {
				drawPanelCanvas.clearDrawing();
			} catch (Exception e) {
				throw new WebforjRuntimeException(e);
			}
		}
		return this;
	}

  /**
   * Toggles the scribble mode on the drawing panel. Scribble mode allows freehand drawing.
   *
   * @param scribble {@code true} to enable scribble mode, {@code false} to disable it.
   * @return The current {@code DrawPanel} instance for chaining calls.
   */
	public DrawPanel setScribble(boolean scribble) {
		if (drawPanelCanvas != null) {
			try {
				drawPanelCanvas.setScribble(scribble);
				return this;
			} catch (BBjException e) {
				throw new WebforjRuntimeException(e);
			}
		}
		this.scribble = scribble;
		return this;
	}

}
