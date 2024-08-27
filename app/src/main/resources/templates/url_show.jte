@import hexlet.code.NamedRoutes
@import hexlet.code.dto.UrlPage
@param UrlPage page = null

@template.layout.page(
    content = @`
        <h1>${page.getUrl().getName()}</h1>
    `,
    footer = @`
        <p>Thank for visiting</p>
    `
)

