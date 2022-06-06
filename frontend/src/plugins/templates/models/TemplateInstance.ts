export type CreateTemplateInstance = {
  locationId: string,
  values: any[]
}

export type TemplateInstance = CreateTemplateInstance & {
  id: string,
  groupId: string,
  templateId: string
}