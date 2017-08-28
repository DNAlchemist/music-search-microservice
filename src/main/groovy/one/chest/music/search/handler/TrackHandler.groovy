/*
 * MIT License
 *
 * Copyright (c) 2017 Mikhalev Ruslan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package one.chest.music.search.handler

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import one.chest.musiclibrary.MusicLibrary
import one.chest.musiclibrary.Track
import one.chest.musiclibrary.TrackLocation
import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

import javax.inject.Inject

import static ratpack.jackson.Jackson.json

@Slf4j
@CompileStatic
class TrackHandler extends GroovyHandler {

    @Inject
    MusicLibrary library

    @Override
    protected void handle(GroovyContext context) {
        try {
            def trackLocation = new TrackLocation(context.pathTokens.albumId as int, context.pathTokens.trackId as int)
            Track track = library.getTrack(trackLocation)
            context.render json(track)
        } catch (e) {
            log.error("Request handling error", e)
            context.response.status 500
            context.render e.message
        }
    }

}
